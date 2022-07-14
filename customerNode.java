public class customerNode {
    int id;
    int arrival_time;
    int no_of_burgers;
    int counter;
    int departure_time;
    int counter_leaving_time;

    public customerNode(int id,int arrival_time,int no_of_burgers){
        this.arrival_time=arrival_time;
        this.id=id;
        this.no_of_burgers=no_of_burgers;
    }
}
class heapNode{
    int a;
    int b;
    int customer_ID;
    int order;
    public heapNode(int a,int b){
        this.a=a;
        this.b=b;
    }
}
class grillNode{
    int start_time;
    int end_time;
    int id;
    int burger_number;
    public grillNode(int a,int b,int c,int d){
        this.start_time=a;
        this.end_time=b;
        this.id=c;
        this.burger_number=d;
    }
}
