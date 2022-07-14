public class queue2 {
    int r=0;
    int f=0;
    int n;
    Object arr[];
    void maxSize(int a){
        n=a+1;
        arr=new Object[a+1];
    }
    public int size(){
        return (n+r-f)%n;
    }
    public boolean isEmpty(){
        return r==f;
    }
    public boolean isFull(){
        return size()==n-1;
    }
    public void enqueue(Object o){
        if(size()!=n-1){
            arr[r]=o;
            r=(r+1)%(n);
        }
    }
    public Object dequeue(){
        if(isEmpty()){
            return null;
        }
        else{
            Object o= arr[f];
            f=(f+1)%n;
            return o;
        }
    }
    public Object front(){
        if(isEmpty()){
            return null;
        }
        else{
            return arr[f];
        }
    }
    public static void main(String[] args) {
        queue2 q=new queue2();
        q.maxSize(1);
        q.enqueue(12);
        q.enqueue(23);
        q.dequeue();
        q.dequeue();
        q.enqueue(612876);
        q.enqueue(62876);
        q.enqueue(6876);
        q.enqueue(600);
        q.enqueue(60);
        System.out.println(q.front());
    }

}
