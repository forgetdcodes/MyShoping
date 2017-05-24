# MyShoping
这也算是我第一个在github上的项目吧！下面是我做项目的一些坑和一些知识点（md还不习惯，看来还得好好的熟悉）



问题：布局文件  几个fragment同时使用的话，其中一个修改文件里面的内容，不知道其他几个受不书影响？就是xml文件是一个模版还是一个实例？
    答案：经过做的项目证明：它真的是一个模版，和一个类一样，所有的人都可以引用它，然后修改他。
    
自定义控件，将参数进行解析，首先得先自定义declare-styleable，然后再自定义控件中将属性取出来，进行解析

recyclerView的用法：简单的分为四步，获取到RecyclerView的控件，设置他的adapter（google强制你使用ViewHolder，将获取View和设置View分离），
设置他的分割线（系统没有默认的分割线，需要自己写），设置他的添加删除动画（系统只有一个默认的），还得自己写监听事件。

问题：Type的转换和获取不懂，
  答案：(ParameterizedType)getClass().getGenericSuperclass().getActualTypeArguments()[0]	
  这句话就是先获得类类型，然后获得超类的类型，将它转化成ParameterizedType，然后获得超类的参数类型，因为参数可能有许多个，
  这里取第一个，然后$Gson$Types.canonicalize（），首先$Gson$Types相当于类适配器,将Java的Type实现类型转化为Gson自己的Type类型实现对象。
  
问题：这儿有一个问题，为什么要把UI操作都放进Handle.post()，而不是把数据取到，让UI在fragment里面操作（这样会造成数据还没取到，
  UI操作就已经开始了（虽然在代码的顺序上取数据操作在UI操作前面），造成运行错误，空指针异常）。
    答案：问题是怎样让UI能用线程执行完的数据（因为主线程和子线程之间没有明确的先后关系），就是在获取数据的子线程中，将UI操作放在获取完数据后面，
    通过Handle.post()传递给UI线程，这样就可以数据获取完成然后再进行UI操作。//这儿其实就是好多框架的原理
    
问题：从网络上面获取到的 图片 让他在点击的时候会弹出来一些东西，又可以让它有点击效果
    答案：一般可用简单的动画，将它旋转，或者透明度放生变化 
    
问题：在购物车那儿，首先删除第一个物品，为什么会发生异常？
    答案：因为在删除缓存List的内容后，还得更新界面。就是notifyItemChange（）这儿错了，其实是notifyItemRemove(),
    看名字就知道，一个是item发生改变时调用，一个是移除时才会调用。
    
java注解的应用：
 
@ContentView(R.layout.main_layout)
protected void onCreat(Bundle bundle){
ViewUtils.Inject(this)
}//这个是使用方法，下面是解释：
//这个是源代码，通过源代码分析
@Target(ElementType.TYPE)  //这段代码是对ContentView的声明和定义
@Retention(RetentionPolicy.RUNTIME)  
public @interface ContentView {  
    int value();  
} 

private static void injectObject(Object handler, ViewFinder finder) {  
  
       Class<?> handlerType = handler.getClass();  
  
       // inject ContentView  
       ContentView contentView = handlerType.getAnnotation(ContentView.class);  
       if (contentView != null) {  
           try {  
               Method setContentViewMethod = handlerType.getMethod("setContentView", int.class);  
               setContentViewMethod.invoke(handler, contentView.value());  
           } catch (Throwable e) {  
               LogUtils.e(e.getMessage(), e);  
           }  
       }}//这个是ViewUtils里面的一个静态注解对象函数，结合着使用例子，首先将activity传递进去，然后通过反射获得activity的Class类型，然后获得注解。
       然后通过反射获得setContentView方法，这个不就是咱们经常用到的方法。然后通过这个方法将注解中的值（也就是R.layout.main_layout），
       就相当于setContentView(R.layout.main_layout)。
       
    
    
       
在对List遍历当中，为什么不能对List本身进行插入删除操作，而Iterator可以，研究一下Iterator原理！！！！！！！！

遍历List有三种方式：
	for：在for循环中修改并没有问题，除非要删除访问的对象，数组越界，或者add会发生死循环
	iterator：在iterator.remove()不会有问题，因为iterator.remove会设置this.expectedModCount = ArrayList.this.modCount;将实际修改的次数赋值给期望修改的次数。
	foreach：本质上是隐式的iterator，由于没有重新设置expectedModCount，当你使用 list.remove() 后遍历执行 iterator.next() 时就会报错。
有一个特例，在删除倒数第二个元素时，不会发生异常。
因为这样会触发hasNext()的时候结果正好为false退出循环不会执行next()

iterator.next()的部分源码：
public E next() {
this.checkForComodification();
...
}

final void checkForComodification() {
if(ArrayList.this.modCount != this.expectedModCount) {
    throw new ConcurrentModificationException();
}
}


java 泛型讲解：
泛型类：

public class Box<T> {
    // T stands for "Type"
    private T t;
    public void set(T t) { this.t = t; }
    public T get() { return t; }
}

泛型方法：

public class Util {
    public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) &&
               p1.getValue().equals(p2.getValue());
    }
}

边界符：

public static <T extends Comparable<T>> int countGreaterThan(T[] anArray, T elem) {
    int count = 0;
    for (T e : anArray)
        if (e.compareTo(elem) > 0)
            ++count;
    return count;
}

通配符：

class Fruit {}
class Apple extends Fruit {}
class Orange extends Fruit {}

static class CovariantReader<T> {
    T readCovariant(List<? extends T> list) {
        return list.get(0);
    }
static void f2() {
    CovariantReader<Fruit> fruitReader = new CovariantReader<Fruit>();
    Fruit f = fruitReader.readCovariant(fruit);
    Fruit a = fruitReader.readCovariant(apples);
}

PECS原则：
“Producer Extends” – 如果你需要一个只读List，用它来produce T，那么使用? extends T。
“Consumer Super” – 如果你需要一个只写List，用它来consume T，那么使用? super T。
如果需要同时读取以及写入，那么我们就不能使用通配符了。
总结一下：假如变量需要用到泛型，则需要在类前面加上泛型，加入方法需要，则需要在方法前面加上就行，不需要在类的前面加。

类型擦除：
java泛型只能用于编译期间的静态类型检查，然后编译器生成相应的代码会擦除相应的类型信息，这样到了运行期间JVM就不知道泛型所代表的类型信息。
泛型擦除到底什么意思呢？


public class Node<T> {
    private T data;
    private Node<T> next;
    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }
    public T getData() { return data; }
    // ...
}


编译器做完相应的类型检查后，到了运行期间就会变成


public class Node {
    private Object data;
    private Node next;
    public Node(Object data, Node next) {
        this.data = data;
        this.next = next;
    }
    public Object getData() { return data; }
    // ...
}




JavaType：

java泛型在运行时会进行泛型擦除，那么怎么在编译时期得到泛型的信息呢？

Type时所有类型的父接口，它的实现类有Class，子接口有ParameterizedType, TypeVariable, GenericArrayType, WildcardType,
