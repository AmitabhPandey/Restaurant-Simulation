public class heap {
    int size=1;
    heapNode[] arr=new heapNode[2];
    public heapNode[] double_size(heapNode[] arr){
        heapNode[] arr_2= new heapNode[2* arr.length];
        for(int i=0;i<arr.length;i++){
            arr_2[i]=arr[i];
        }
        return arr_2;
    }
    public void insert(int a,int b){

        if(arr.length-1==size){
            arr=double_size(arr);
        }
        if(size==1){
            arr[size]=new heapNode(a,b);
            size++;
        }
        else{
            int temp=size;
            arr[temp]=new heapNode(a,b);
            while(temp!=1 && arr[temp/2].a>arr[temp].a){
                arr[temp]=arr[temp/2];
                arr[temp/2]=new heapNode(a,b);
                temp=temp/2;
            }
            size++;
        }
    }
    public heapNode finMin(){
        return arr[1];
    }
    public int deleteMin(){
        int temp=arr[1].a;
        arr[1]=arr[size-1];
        size--;
        percolate_down(1,arr[1].a,arr[1].b);
        return temp;

    }
    public void percolate_down(int i,int x,int y){
        if(2*i>size-1){
            arr[i].a=x;
            arr[i].b=y;
        }
        else if(2*i==size-1){
            if(arr[2*i].a<x){
                arr[i]=new heapNode(arr[2*i].a,arr[2*i].b);
                arr[2*i].a=x;
                arr[2*i].b=y;
            }
            else{
                arr[i].a=x;
                arr[i].b=y;
            }

        }
        else{
            int j;
            if(arr[2*i].a>arr[2*i+1].a){
                j=2*i+1;
            }
            else if(arr[2*i].a<arr[2*i+1].a){
                j=2*i;
            }
            else{
                if(arr[2*i].b>arr[2*i+1].b){
                    j=2*i+1;
                }
                else{
                    j=2*i;
                }
            }
            if(arr[j].a<x){
                arr[i]=new heapNode(arr[j].a,arr[j].b);
                arr[j].a=x;
                arr[j].b=y;
                percolate_down(j,x,y);
            }
            else{
                arr[i].a=x;
                arr[i].b=y;
            }
        }
    }
    public void print_heap(){
        for(int i=1;i<size;i++){
            System.out.print(arr[i].a+" ");
        }
    }

    public static void main(String[] args) {
        heap h= new heap();
        h.insert(7,1    );
        h.insert(9,2);
        h.insert(6,3);
        h.insert(11,4);
        h.insert(5,6);
        h.deleteMin();
        h.deleteMin();
        h.deleteMin();
        h.print_heap();


    }


}
 class heap2 {
    int size=1;
    heapNode[] arr=new heapNode[2];
    public heapNode[] double_size(heapNode[] arr){
        heapNode[] arr_2= new heapNode[2* arr.length];
        for(int i=0;i<arr.length;i++){
            arr_2[i]=arr[i];
        }
        return arr_2;
    }
    public void insert(heapNode node){
        int a= node.a;
        int b= node.b;
        int c= node.order;
        int d= node.customer_ID;
        if(arr.length-1==size){
            arr=double_size(arr);
        }
        if(size==1){
            arr[size]=node;
            arr[size].customer_ID=d;
            arr[size].order=c;
            size++;
        }
        else{
            int temp=size;
            arr[temp]=node;
            while(temp!=1 && arr[temp/2].a>arr[temp].a){
                arr[temp]=arr[temp/2];
                arr[temp/2]=node;
                arr[temp/2].customer_ID=d;
                arr[temp/2].order=c;
                temp=temp/2;
            }
            size++;
        }
    }
    public boolean isEmpty(){
        return size==1;
    }
    public heapNode finMin(){
        return arr[1];
    }
    public heapNode deleteMin(){
        heapNode temp=arr[1];
        if(size==2){
            arr[1]=null;
            size--;
            return temp;

        }
        arr[1]=arr[size-1];
        size--;
        if(size!=1){
            percolate_down(1,arr[1].a,arr[1].b,arr[1].order,arr[1].customer_ID);
            return temp;
        }
        else{
            return null;
        }


    }
    public void percolate_down(int i,int x,int y,int order, int customerID){
        if(2*i>size-1){
            arr[i].a=x;
            arr[i].b=y;
            arr[i].order=order;
            arr[i].customer_ID=customerID;
        }
        else if(2*i==size-1){
            if(arr[2*i].a<x){
                arr[i]=new heapNode(arr[2*i].a,arr[2*i].b);
                arr[i].order=arr[2*i].order;
                arr[i].customer_ID=arr[2*i].customer_ID;
                arr[2*i].a=x;
                arr[2*i].b=y;
                arr[2*i].order=order;
                arr[2*i].customer_ID=customerID;
            }
            else{
                arr[i].a=x;
                arr[i].b=y;
                arr[i].order=order;
                arr[i].customer_ID=customerID;
            }

        }
        else{
            int j;
            if(arr[2*i].a>arr[2*i+1].a){
                j=2*i+1;
            }
            else if(arr[2*i].a<arr[2*i+1].a){
                j=2*i;
            }
            else{
                if(arr[2*i].b>arr[2*i+1].b){
                    j=2*i;
                }
                else{
                    j=2*i+1;
                }
            }
            if(arr[j].a<x){
                arr[i]=new heapNode(arr[j].a,arr[j].b);
                arr[i].order=arr[j].order;
                arr[i].customer_ID=arr[j].customer_ID;
                arr[j].a=x;
                arr[j].b=y;
                arr[j].order=order;
                arr[j].customer_ID=customerID;
                percolate_down(j,x,y,order,customerID);
            }
            else{
                arr[i].a=x;
                arr[i].b=y;
                arr[i].order=order;
                arr[i].customer_ID=customerID;
            }
        }
    }
    public void print_heap(){
        for(int i=1;i<size;i++){
            System.out.println(arr[i].order+" ");
        }
    }

    public static void main(String[] args) {
        heap2 h= new heap2();
        h.insert(new heapNode(7,8));
        h.insert(new heapNode(2,3));
        h.insert(new heapNode(1,5));
        h.insert(new heapNode(6,7));
        h.insert(new heapNode(4,2));

        h.print_heap();


    }


}
