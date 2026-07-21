package model;

public class divisa {


        private String code;
        private String name;

        public divisa(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return code;
        }
}