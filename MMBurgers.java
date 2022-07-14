

public class MMBurgers implements MMBurgersInterface {

    queue1[] counter_queues;
    queue2 griddle=new queue2();
    queue1 griddle_waiting=new queue1();
    heap2 counter_leaving=new heap2();
    customerNode[] customers= new customerNode[2];

    public customerNode[] getCustomers() {
        customers[0]=new customerNode(0,0,0);
        return customers;
    }

    heap queue_sizes= new heap();

    int current_id=1;
    int global_time =0;
    public boolean isEmpty(){
        //your implementation
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        boolean flag=false;
        for(int i=1;i<counter_queues.length;i++){
            if(counter_queues[i].isEmpty()==false){
                flag=true;
            }
        }
        if(flag==false && griddle.isEmpty() &&griddle_waiting.isEmpty() ){
            return true;
        }
        else{
            return false;
        }

    }

    public void setK(int k) throws IllegalNumberException{
        //your implementation
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        if(k<1){
            throw new IllegalNumberException("Invalid Value for k");
        }
        else{
            counter_queues= new queue1[k+1];
            for(int i=1;i<=k;i++){
                counter_queues[i]=new queue1();
                queue_sizes.insert(0,i);
            }
        }




    }

    public void setM(int m) throws IllegalNumberException{
        //your implementation
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        if(m<1){
            throw new IllegalNumberException("Invalid Value for m");
        }
        else{
            griddle.maxSize(m);
        }


    }

    public void advanceTime(int t) throws IllegalNumberException{
        //your implementation
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        if(t<global_time){
            throw new IllegalNumberException("Invalid value of time input");
        }
        else{
            int time;
            while(counter_leaving.isEmpty()==false && counter_leaving.finMin().a<=t){
//            System.out.println(counter_leaving.finMin().order);
                heapNode node= counter_leaving.deleteMin();
                if(node!=null){
                    time=node.a;

                    if(griddle.isEmpty()==false){
                        grillNode temporary=(grillNode) griddle.front();
                        while(temporary!=null && temporary.end_time<=time){
                            grillNode temp_leaving_time=(grillNode) griddle.dequeue();
                            if(temp_leaving_time.burger_number==customers[temp_leaving_time.id].no_of_burgers){
                                customers[temp_leaving_time.id].departure_time=temp_leaving_time.end_time+1;
                            }
                            if(griddle_waiting.isEmpty()==false){
                                grillNode nextPatty=(grillNode) griddle_waiting.dequeue();
                                nextPatty.end_time=temp_leaving_time.end_time+10;
                                griddle.enqueue(nextPatty);
                            }
                            temporary=(grillNode) griddle.front();
                        }
                    }
                    for(int i=1;i<=node.order;i++){

                        if(griddle.size()!=griddle.n-1){

                            griddle.enqueue(new grillNode(node.a, node.a+10, node.customer_ID, i));
                        }
                        else{
//                        grillNode temp=(grillNode) griddle.front();
//                        if(temp!=null){
//                            time=temp.end_time;
//                            if(time<=t){
//                                grillNode temp_leaving_time=(grillNode) griddle.dequeue();
//                                if(griddle_waiting.isEmpty()==false){
//                                    grillNode nextPatty=(grillNode) griddle_waiting.dequeue();
//                                    nextPatty.end_time=temp_leaving_time.end_time+10;
//                                    griddle.enqueue(nextPatty);
//                                }
//
//                            }
//                        }
                            griddle_waiting.enqueue(new grillNode(node.a,node.a+10,node.customer_ID,i));
                        }
                    }



                }
                else{
                    break;
                }


            }
            grillNode tempo=(grillNode) griddle.front();
            while(tempo!=null && tempo.end_time<=t){
                grillNode a=(grillNode) griddle.dequeue();
                if(a.burger_number==customers[a.id].no_of_burgers){
                    customers[a.id].departure_time=a.end_time+1;
                }
                if(griddle_waiting.isEmpty()==false){
                    grillNode nextPatty=(grillNode) griddle_waiting.dequeue();
                    nextPatty.end_time=a.end_time+10;
                    griddle.enqueue(nextPatty);

                }
                tempo=(grillNode) griddle.front();
            }
            for(int i=1;i< counter_queues.length;i++){
                customerNode tmp = (customerNode) counter_queues[i].front();
                while(tmp!=null && tmp.counter_leaving_time<=t){
                    counter_queues[i].dequeue();
                    tmp=(customerNode) counter_queues[i].front();
                }
            }
            heap new_queue_sizes=new heap();
            for(int i=1;i<counter_queues.length;i++){
                new_queue_sizes.insert(counter_queues[i].size(),i);
            }
            queue_sizes=new_queue_sizes;
            global_time=t;
        }

    }

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException{
        //your implementation
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        if(id!=current_id){
            throw new IllegalNumberException("Incorrect ID");
        }
        else if(t<global_time){
            throw new IllegalNumberException("Incorrect value for time input");
        }
        else if(id==1){
            customerNode newCustomer=new customerNode(id,t,numb);
            counter_queues[1].enqueue(newCustomer);
            newCustomer.arrival_time=t;
            customers[id]=newCustomer;
            newCustomer.counter=queue_sizes.finMin().b;
//            System.out.println(newCustomer.counter);
            newCustomer.counter_leaving_time= newCustomer.arrival_time+ newCustomer.counter*(counter_queues[newCustomer.counter].size());
            heapNode node= new heapNode(newCustomer.counter_leaving_time, newCustomer.counter);
            node.customer_ID=id;
            node.order=numb;
            counter_leaving.insert(node);
            queue_sizes.finMin().a+=1;
            queue_sizes.percolate_down(1,queue_sizes.finMin().a,queue_sizes.finMin().b);
            current_id+=1;
            global_time=t;
//            System.out.println(counter_leaving.finMin().order);
        }
        else{
            customerNode newCustomer= new customerNode(id,t,numb);
            if(id>customers.length-1){
                customerNode[] customers2= new customerNode[2*customers.length];
                for(int i=0;i<customers.length;i++){
                    customers2[i]=customers[i];
                }
                customers=customers2;
                customers[id]=newCustomer;
                current_id+=1;
                global_time=t;
                newCustomer.arrival_time=t;
                for(int i=1;i< counter_queues.length;i++){
                    customerNode tmp = (customerNode) counter_queues[i].front();
                    while(tmp!=null && tmp.counter_leaving_time<=t){
                        counter_queues[i].dequeue();
                        tmp=(customerNode) counter_queues[i].front();
                    }
                }
                heap new_queue_sizes=new heap();
                for(int i=1;i<counter_queues.length;i++){
                    new_queue_sizes.insert(counter_queues[i].size(),i);
                }
                queue_sizes=new_queue_sizes;
                int first_person_waiting_time;
                newCustomer.counter=queue_sizes.finMin().b;
//                System.out.println(newCustomer.counter);
                counter_queues[newCustomer.counter].enqueue(newCustomer);
                customerNode temp=(customerNode) counter_queues[newCustomer.counter].front();
                if(temp==newCustomer){
                    first_person_waiting_time=newCustomer.counter;
                }
                else{
                    first_person_waiting_time=temp.counter_leaving_time-t;
                }

                newCustomer.counter_leaving_time= first_person_waiting_time+newCustomer.arrival_time+ newCustomer.counter*(counter_queues[newCustomer.counter].size()-1);
                heapNode node= new heapNode(newCustomer.counter_leaving_time, newCustomer.counter);
                node.customer_ID=id;
                node.order=numb;
                counter_leaving.insert(node);
//                counter_leaving.deleteMin();
//                System.out.println(counter_leaving.finMin().order);
            }
            else{
                customers[id]=newCustomer;
                current_id+=1;
                global_time=t;
                newCustomer.arrival_time=t;
                for(int i=1;i< counter_queues.length;i++){
                    customerNode tmp = (customerNode) counter_queues[i].front();
                    while(tmp!=null && tmp.counter_leaving_time<=t){
                        counter_queues[i].dequeue();
                        tmp=(customerNode) counter_queues[i].front();
                    }
                }
                heap new_queue_sizes=new heap();
                for(int i=1;i<counter_queues.length;i++){
                    new_queue_sizes.insert(counter_queues[i].size(),i);
                }
                queue_sizes=new_queue_sizes;
                int first_person_waiting_time;
                newCustomer.counter=queue_sizes.finMin().b;
//                System.out.println(newCustomer.counter);
                counter_queues[newCustomer.counter].enqueue(newCustomer);
                customerNode temp=(customerNode) counter_queues[newCustomer.counter].front();
                if(temp==newCustomer){
                    first_person_waiting_time=newCustomer.counter;
                }
                else{
                    first_person_waiting_time=temp.counter_leaving_time-t;
                }

                newCustomer.counter_leaving_time= first_person_waiting_time+newCustomer.arrival_time+ newCustomer.counter*(counter_queues[newCustomer.counter].size()-1);
                heapNode node= new heapNode(newCustomer.counter_leaving_time, newCustomer.counter);
                node.customer_ID=id;
                node.order=numb;
                counter_leaving.insert(node);
//                counter_leaving.deleteMin();
//                System.out.println(counter_leaving.finMin().order);
            }
            queue_sizes.finMin().a+=1;
            queue_sizes.percolate_down(1,queue_sizes.finMin().a,queue_sizes.finMin().b);
        }



    }



    public int customerState(int id, int t) throws IllegalNumberException{
        //your implementation
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        advanceTime(t);
        if(customers[id]!=null){
            if(customers[id].arrival_time>t){
                return 0;
            }
            else if(t>=customers[id].arrival_time && t<customers[id].counter_leaving_time){
//                System.out.println("Here1");
                return customers[id].counter;
            }
            else if(t>=customers[id].counter_leaving_time && customers[id].departure_time==0 ){
//                System.out.println("Here2");
                return counter_queues.length;

            }
            else{
//                System.out.println("Here3");
                return (counter_queues.length+1);
            }
        }
        else{
            throw new IllegalNumberException("ID not Found");
        }

    }

    public int griddleState(int t) throws IllegalNumberException{
        //your implementation
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");

        int time;
        while(counter_leaving.isEmpty()==false && counter_leaving.finMin().a<=t){
//            System.out.println(counter_leaving.finMin().order);
            heapNode node= counter_leaving.deleteMin();
            if(node!=null){
                time=node.a;

                if(griddle.isEmpty()==false){
                    grillNode temporary=(grillNode) griddle.front();
                    while(temporary!=null && temporary.end_time<=time){
                        grillNode temp_leaving_time=(grillNode) griddle.dequeue();
                        if(temp_leaving_time.burger_number==customers[temp_leaving_time.id].no_of_burgers){
                            customers[temp_leaving_time.id].departure_time=temp_leaving_time.end_time+1;
                        }
                        if(griddle_waiting.isEmpty()==false){
                            grillNode nextPatty=(grillNode) griddle_waiting.dequeue();
                            nextPatty.end_time=temp_leaving_time.end_time+10;
                            griddle.enqueue(nextPatty);
                        }
                        temporary=(grillNode) griddle.front();
                    }
                }
                for(int i=1;i<=node.order;i++){

                    if(griddle.size()!=griddle.n-1){

                        griddle.enqueue(new grillNode(node.a, node.a+10, node.customer_ID, i));
                    }
                    else{
//                        grillNode temp=(grillNode) griddle.front();
//                        if(temp!=null){
//                            time=temp.end_time;
//                            if(time<=t){
//                                grillNode temp_leaving_time=(grillNode) griddle.dequeue();
//                                if(griddle_waiting.isEmpty()==false){
//                                    grillNode nextPatty=(grillNode) griddle_waiting.dequeue();
//                                    nextPatty.end_time=temp_leaving_time.end_time+10;
//                                    griddle.enqueue(nextPatty);
//                                }
//
//                            }
//                        }
                        griddle_waiting.enqueue(new grillNode(node.a,node.a+10,node.customer_ID,i));
                    }
                }



            }
            else{
                break;
            }

        }
        grillNode tempo=(grillNode) griddle.front();
        while(tempo!=null && tempo.end_time<=t){
            grillNode a=(grillNode) griddle.dequeue();
            if(a.burger_number==customers[a.id].no_of_burgers){
                customers[a.id].departure_time=a.end_time+1;
            }
            if(griddle_waiting.isEmpty()==false){
                grillNode nextPatty=(grillNode) griddle_waiting.dequeue();
                nextPatty.end_time=a.end_time+10;
                griddle.enqueue(nextPatty);

            }
            tempo=(grillNode) griddle.front();
        }
        global_time=t;
        return griddle.size();
    }

    public int griddleWait(int t) throws IllegalNumberException{
        //your implementation
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        advanceTime(t);
        return griddle_waiting.size();
    }

    public int customerWaitTime(int id) throws IllegalNumberException{
        //your implementation
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        if(customers[id]!=null){
            return customers[id].departure_time-customers[id].arrival_time;
        }
        else{
            throw new IllegalNumberException("ID not Found");
        }

    }

    public float avgWaitTime(){
        //your implementation
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
        int total_waiting_time=0;
        for(int i=0;i<customers.length;i++){
            if(customers[i]!=null){
                total_waiting_time+= customers[i].departure_time-customers[i].arrival_time;
            }
        }
        float ans= (float) total_waiting_time/(float) (current_id-1);
        return ans;
    }


}
