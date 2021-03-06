package com.cmfs.builder.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import static com.cmfs.builder.compiler.Constants.BUILDER_SUFFIX;
import static com.cmfs.builder.compiler.Constants.DELIMITER;

/**
 * @author cmfs
 */

public final class Utils {

    static String name(QualifiedNameable element) {
        return element.getQualifiedName().toString();
    }

    static String sname(QualifiedNameable element) {
        return element.getSimpleName().toString();
    }

    static String getBuilderSimpleName(String source, String target) {
        return concat(source, DELIMITER, target, DELIMITER, BUILDER_SUFFIX);
    }

    static ClassName getClassName(QualifiedNameable element) {
        return ClassName.bestGuess(element.getQualifiedName().toString());
    }

    static String getClassSimpleName(QualifiedNameable element) {
        return getClassName(element).simpleName();
    }

    static TypeVariableName getTypeVariableName(String typeName) {
        return TypeVariableName.get(typeName);
    }

    static ParameterizedTypeName getParameterizedTypeName(String className, String... typeVariableNames) {
        TypeVariableName[] typeVariableNameArr = new TypeVariableName[typeVariableNames.length];
        for (int i = 0; i < typeVariableNames.length; i++) {
            typeVariableNameArr[i] = TypeVariableName.get(typeVariableNames[i]);
        }
        return ParameterizedTypeName.get(ClassName.bestGuess(className), typeVariableNameArr);
    }

    static ParameterizedTypeName getParameterizedTypeName(Class cls, String... typeVariableNames) {
        TypeVariableName[] typeVariableNameArr = new TypeVariableName[typeVariableNames.length];
        for (int i = 0; i < typeVariableNames.length; i++) {
            typeVariableNameArr[i] = TypeVariableName.get(typeVariableNames[i]);
        }
        return ParameterizedTypeName.get(ClassName.get(cls), typeVariableNameArr);
    }

    static ParameterizedTypeName getParameterizedTypeName(Class cls, Class... classes) {
        TypeName[] typeNameArr = new TypeName[classes.length];
        for (int i = 0; i < classes.length; i++) {
            typeNameArr[i] = TypeVariableName.get(classes[i]);
        }
        return ParameterizedTypeName.get(ClassName.get(cls), typeNameArr);
    }

    static ParameterizedTypeName getParameterizedTypeName(ClassName className, String... typeVariableNames) {
        TypeVariableName[] typeVariableNameArr = new TypeVariableName[typeVariableNames.length];
        for (int i = 0; i < typeVariableNames.length; i++) {
            typeVariableNameArr[i] = TypeVariableName.get(typeVariableNames[i]);
        }
        return ParameterizedTypeName.get(className, typeVariableNameArr);
    }

    static ParameterizedTypeName getParameterizedTypeName(Class cls, TypeName... typeNames) {
        return ParameterizedTypeName.get(ClassName.get(cls), typeNames);
    }

    static ParameterizedTypeName getParameterizedTypeName(ClassName className, TypeName... typeNames) {
        return ParameterizedTypeName.get(className, typeNames);
    }

    public static String capitalize(String name) {
        if (name != null && name.length() != 0) {
            char[] chars = name.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        } else {
            return name;
        }
    }

    public static String decapitalize(String name) {
        if (name != null && name.length() != 0) {
            if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) && Character.isUpperCase(name.charAt(0))) {
                return "p" + name;
            } else {
                char[] chars = name.toCharArray();
                chars[0] = Character.toLowerCase(chars[0]);
                return new String(chars);
            }
        } else {
            return name;
        }
    }

    public static String decapitalize2(String name) {
        if (name != null && name.length() != 0) {
            char[] chars = name.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return new String(chars);
        } else {
            return name;
        }
    }

    public static List<VariableElement> getFieldElement(Elements elementUtils, TypeElement typeElement) {
        List<? extends Element> readElementList = elementUtils.getAllMembers(typeElement);
        List<VariableElement> fieldElementList = new ArrayList<>(readElementList.size());
        for (Element element : readElementList) {
            if (element.getKind() == ElementKind.FIELD) {
                fieldElementList.add((VariableElement) element);
            }
        }
        return fieldElementList;
    }

    public static String concat(String... strings) {
        StringBuilder builder = new StringBuilder(strings.length * 5);
        for (String s : strings) {
            builder.append(s);
        }
        return builder.toString();
    }

    public static <T> T[] toArray(Set<T> set, Class<T> tClass) {
        return (T[]) Array.newInstance(tClass, set.size());
    }

}
