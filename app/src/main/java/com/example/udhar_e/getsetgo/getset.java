package com.example.udhar_e.getsetgo;

public class getset {

        private String name;
        private String amount;
        private String date;
        private String desc;
        private String type;
        private int id;



        public String getName(){
            return name;
        }

        public void setName(){
            this.name=name;
        }

    public String getAmount(){
        return amount;
    }

    public void setAmount(){
        this.amount=amount;
    }


    public String getDate(){
        return date;
    }

    public void setDate(){
        this.date=date;
    }

    public String getDesc(){return desc;}

    public void setDesc(){
        this.desc=desc;
    }


    public int getId(){
        return id;
    }

    public void setId(){
        this.id=id;
    }

    public String getType(){
        return type;
    }

    public void setType(){
        this.type=type;
    }




    public getset(String name,String amount,String date, String desc, String type){
            this.name=name;
            this.amount=amount;
            this.date=date;
            this.desc=desc;
            this.type=type;

    }




}
