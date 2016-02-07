
package com.hussamsherif.musicretriever;

import java.util.ArrayList;

public class MusicQuery {

    private String projection = "" ;
    private String[] selectionArguments ;
    private String sortBy;

    private MusicQuery(Builder builder){
        selectionArguments = new String[builder.getArguments().size()];
        //First argument is always the Main Argument
            projection += builder.arguments.get(0).getWhereCondition();
            selectionArguments[0] = builder.arguments.get(0).getWhereVal();

        for (int i = 1; i < builder.getArguments().size(); i++) {
            QueryArgument argument = builder.getArguments().get(i);
            projection += argument.getWhereCondition();
            selectionArguments[i] = argument.getWhereVal() ;
        }
        sortBy = builder.sortBy;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String[] getSelectionArguments() {
        return selectionArguments;
    }

    public String getProjection() {
        return projection;
    }

    public static class Builder{

        private ArrayList<QueryArgument> arguments = new ArrayList<>();
        private String sortBy ;
        private boolean isMainArgumentCalled ;

        public Builder addMainArgument(String whereCondition , int operator , String whereVal){
            if (isMainArgumentCalled)
                throw new IllegalStateException("addMainArgument has been already called");

            if (isAccepted(whereCondition , operator , whereVal)){
                isMainArgumentCalled = true;
                addArgument(whereCondition , operator , whereVal,true);
            }

            return this;
        }

        public Builder and(String whereCondition , int operator , String whereVal){
            if (isAccepted(whereCondition , operator , whereVal))
                addArgument("AND " + whereCondition , operator , whereVal,false);
            return this;
        }

        public Builder or(String whereCondition , int operator , String whereVal){
            if (isAccepted(whereCondition , operator , whereVal))
                addArgument("OR " + whereCondition , operator , whereVal ,false);
            return this;
        }

        private void addArgument(String whereCondition, int operator , String whereVal ,boolean first){
            switch (operator){
                case Operator.EQUALS:
                    QueryArgument equalsArgument = new QueryArgument(whereCondition + "=? ", whereVal);
                    if (first)
                        arguments.add(equalsArgument);
                    else
                        arguments.add(equalsArgument);
                    break;
                case Operator.LIKE:
                    QueryArgument likeArgument= new QueryArgument(whereCondition + " LIKE ? ", "%"+whereVal+"%");
                    if (first)
                        arguments.add(0 ,likeArgument);
                    else
                        arguments.add(likeArgument);
                    break;
            }
        }

        private boolean isAccepted(String whereCondition , int operator , String whereVal) {
            if (whereCondition == null)
                throw new NullPointerException("where condition must not be null");

            if (!RowConstants.checkIfExists(whereCondition))
                throw new IllegalArgumentException("Where condition not recognized , Did you get it from the RowConstants class ?");

            if (whereVal == null)
                throw new NullPointerException("Where value must not be null");

            if (!Operator.checkIfExists(operator))
                throw new IllegalArgumentException("Operator not recognized , Did you get it from the Operator class ?");

            return true;
        }

        public MusicQuery build(){
            if (isMainArgumentCalled)
                return new MusicQuery(this);
            else{
                throw new IllegalStateException("addMainArgument() must be called");
            }
        }

        public Builder sortBy(String sortBy){
            if (!RowConstants.checkIfExists(sortBy))
                throw new IllegalArgumentException("sortBy input is not recognized , Did you get it from the RowConstants class ?");

            this.sortBy = sortBy;
            return this ;
        }

        public ArrayList<QueryArgument> getArguments() {
            return arguments;
        }
    }

    private static class QueryArgument {
        private String whereCondition ;
        private String whereVal ;

        public QueryArgument(String whereCondition, String whereVal){
            this.whereCondition = whereCondition ;
            this.whereVal = whereVal;
        }

        public String getWhereCondition() {
            return whereCondition;
        }

        public String getWhereVal() {
            return whereVal;
        }

    }
}
