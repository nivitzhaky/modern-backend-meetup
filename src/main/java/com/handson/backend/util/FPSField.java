package com.handson.backend.util;


public class FPSField {
    String field;
    String alias;

    public String getField() {
        return field;
    }

    public String getAlias() {
        return alias;
    }

    public static final class FPSFieldBuilder {
        String field;
        String alias;

        private FPSFieldBuilder() {
        }

        public static FPSFieldBuilder aFPSField() {
            return new FPSFieldBuilder();
        }

        public FPSFieldBuilder field(String field) {
            this.field = field;
            return this;
        }

        public FPSFieldBuilder alias(String alias) {
            this.alias = alias;
            return this;
        }

        public FPSField build() {
            FPSField fPSField = new FPSField();
            fPSField.field = this.field;
            fPSField.alias = this.alias;
            return fPSField;
        }
    }
}
