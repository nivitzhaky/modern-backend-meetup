package com.handson.backend.util;


public class FPSCondition {
    private String condition;
    private String parameterName;
    private Object value;

    public String getCondition() {
        return condition;
    }

    public String getParameterName() {
        return parameterName;
    }

    public Object getValue() {
        return value;
    }


    public static final class FPSConditionBuilder {
        private String condition;
        private String parameterName;
        private Object value;

        private FPSConditionBuilder() {
        }

        public static FPSConditionBuilder aFPSCondition() {
            return new FPSConditionBuilder();
        }

        public FPSConditionBuilder condition(String condition) {
            this.condition = condition;
            return this;
        }

        public FPSConditionBuilder parameterName(String parameterName) {
            this.parameterName = parameterName;
            return this;
        }

        public FPSConditionBuilder value(Object value) {
            this.value = value;
            return this;
        }

        public FPSCondition build() {
            FPSCondition fPSCondition = new FPSCondition();
            fPSCondition.value = this.value;
            fPSCondition.condition = this.condition;
            fPSCondition.parameterName = this.parameterName;
            return fPSCondition;
        }
    }
}
