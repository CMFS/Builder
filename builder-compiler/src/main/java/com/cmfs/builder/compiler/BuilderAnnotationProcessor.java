package com.cmfs.builder.compiler;

import com.cmfs.builder.annotations.Builder;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static com.cmfs.builder.compiler.Constants.BUILDER_BUNDLE;
import static com.cmfs.builder.compiler.Constants.BUILDER_SUFFIX;
import static com.cmfs.builder.compiler.Constants.COMMENT;
import static com.cmfs.builder.compiler.Constants.DELIMITER;
import static com.cmfs.builder.compiler.Constants.PACKAGE;

@AutoService(Processor.class)
public class BuilderAnnotationProcessor extends BaseAnnotationProcessor {

    private Set<BuilderMapper> builderMapperSet = new HashSet<>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        processBuilderAnnotations(roundEnvironment);
        generateBuilderBundle(builderMapperSet);
        return false;
    }

    private void processBuilderAnnotations(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Builder.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                continue;
            }
            TypeElement modelTypeElement = (TypeElement) element;

            PackageElement builderPackageElement = mElementUtils.getPackageOf(modelTypeElement);
            String builderPackageName = builderPackageElement.getQualifiedName().toString();
            String builderSimpleName = modelTypeElement.getSimpleName().toString() + DELIMITER + BUILDER_SUFFIX;


            ClassName iBuilderClassName = ClassName.bestGuess("com.cmfs.builder.IBuilder");
            ClassName modelTypeName = ClassName.bestGuess(modelTypeElement.getQualifiedName().toString());
            ParameterizedTypeName interfaceName = ParameterizedTypeName.get(iBuilderClassName, modelTypeName);

            String paramName = "$$" + Utils.decapitalize2(modelTypeElement.getSimpleName().toString());

            List<VariableElement> fieldElementList = Utils.getFieldElement(mElementUtils, modelTypeElement);
            List<FieldSpec> fieldSpecList = new ArrayList<>(fieldElementList.size());
            List<MethodSpec> methodSpecList = new ArrayList<>(fieldElementList.size());
            CodeBlock.Builder setCodeBlockBuilder = CodeBlock.builder();
            ClassName modelBuilderClassName = ClassName.get(builderPackageName, builderSimpleName);
            for (final VariableElement fieldElement : fieldElementList) {
                if (fieldElement.getModifiers() == null
                        || fieldElement.getModifiers().contains(Modifier.PRIVATE)
                        || fieldElement.getModifiers().contains(Modifier.FINAL)) {
                    continue;
                }
                String fieldName = fieldElement.getSimpleName().toString();

                fieldSpecList.add(FieldSpec.builder(ClassName.get(fieldElement.asType()), fieldName)
                        .build()
                );

                methodSpecList.add(MethodSpec.methodBuilder(fieldName)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(modelBuilderClassName)
                        .addParameter(ClassName.get(fieldElement.asType()), fieldName)
                        .addCode("this.$L = $L;\n", fieldName, fieldName)
                        .addCode("return this;\n")
                        .build()
                );
                setCodeBlockBuilder.add("$L.$L = $L;\n", paramName, fieldName, fieldName);
            }


            MethodSpec.Builder buildMethodSpecBuilder = MethodSpec.methodBuilder("build")
                    .addModifiers(Modifier.PUBLIC, Modifier.SYNCHRONIZED)
                    .returns(modelTypeName)
                    .addCode("$T $L = new $T();\n", modelTypeName, paramName, modelTypeName)
                    .addCode(setCodeBlockBuilder.build())
                    .addCode("return $L;\n", paramName);

            builderMapperSet.add(new BuilderMapper(modelTypeName, modelBuilderClassName));

            TypeSpec builderTypeSpec = TypeSpec.classBuilder(builderSimpleName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addSuperinterface(interfaceName)
                    .addFields(fieldSpecList)
                    .addMethods(methodSpecList)
                    .addMethod(buildMethodSpecBuilder.build())
                    .build();

            generateJavaFile(JavaFile.builder(builderPackageName, builderTypeSpec));
        }
    }

    private void generateBuilderBundle(Set<BuilderMapper> builderMapperSet) {
        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(BUILDER_BUNDLE)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        List<MethodSpec> methodSpecList = new ArrayList<>(builderMapperSet.size());
        for (BuilderMapper mapper : builderMapperSet) {
            String modelSimpleName = mapper.modelClassName.simpleName();
            MethodSpec.Builder msBuilder = MethodSpec.methodBuilder(Utils.concat("new", Utils.capitalize(modelSimpleName), BUILDER_SUFFIX))
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(mapper.modelBuilderClassName)
                    .addCode("return new $T();\n", mapper.modelBuilderClassName);
            methodSpecList.add(msBuilder.build());
        }
        typeSpecBuilder.addMethods(methodSpecList);
        generateJavaFile(JavaFile.builder(PACKAGE, typeSpecBuilder.build()));
    }

    private void generateJavaFile(JavaFile.Builder javaFileBuilder) {
        try {
            javaFileBuilder
                    .addFileComment(COMMENT)
                    .build()
                    .writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> supportedAnnotations = new HashSet<>();
        supportedAnnotations.add(Builder.class);
        return supportedAnnotations;
    }

    static class BuilderMapper {
        private ClassName modelClassName;
        private ClassName modelBuilderClassName;

        public BuilderMapper(ClassName modelClassName, ClassName modelBuilderClassName) {
            this.modelClassName = modelClassName;
            this.modelBuilderClassName = modelBuilderClassName;
        }
    }
}
