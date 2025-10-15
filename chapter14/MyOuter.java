package chapter14;

class MyOuter {
  private int x;
  private MyInner inner = new MyInner();

  public void doStuff() {
    inner.go();
  }

  class MyInner {
    void go() {
      x = 42;
    }
  } 

}
