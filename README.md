# Builder

使用了`@Builder`注解在编译期间生成对应的Builder类。如`Model`对象会在同一包下生成`Model$$Builder`类。

可调用`BuilderBundle`中的对应方法（格式为`new{Model}Builder`）创建Builder对象。如`BuilderBundle.newModelBuilder()`创建`Model$$Builder`实例。

每个字段都会根据字段名称在Builder类生成一个对应的方法。

> 使用`private`和`final`修饰的属性将不会添加到对应的Builder类中

```java
@Builder
public class Model {
  String s;
}

// 编译时生成Model$$Builder
public final class Model$$Builder implements IBuilder<Model> {
    String s;
    public Model$$Builder s(String s) {
        this.s = s;
        return this;
    }
    public synchronized Model build() {
        Model model = new Model();
        model.s = s;
        return model;
    }
}

// 同时会生成BuilderBundle
public final class BuilderBundle {
    public static Model$$Builder newModelBuilder() {
        return new Model$$Builder();
    }
}

public class Test {
  public static void main(String[] args){
    Model model = BuilderBundle
        .newModelBuilder()
        .s(s)
        .build();
  }
}

```