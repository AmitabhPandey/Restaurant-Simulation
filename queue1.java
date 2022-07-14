public class queue1 {
    int f=0;
    int r=0;
    Object arr[]= new Object[1];
    public void enqueue(Object O){
        if(r==arr.length-1){
            Object[] arr2= new Object[2* arr.length];
            for(int i=0;i< arr.length;i++){
                arr2[i]=arr[i];
            }
            arr=arr2;
            arr[r]=O;
            r+=1;
        }
        else{
            arr[r]=O;
            r+=1;
        }

    }
    public Object dequeue(){
        if(r==f){
            return null;
        }
        else{
            int temp = f;
            f=f+1;
            return arr[temp];
        }
    }
    public int size(){
        return r-f;
    }
    public boolean isEmpty(){
        return r==f;
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
        queue1 q=new queue1();
        q.enqueue(new customerNode(0,0,0));

        System.out.println(q.front());
    }
}
