package Semantics;

public class classType implements Type{
    private String name;

    public classType(String className) {
        this.name = className;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Type type) {
        classType temp;
        if (type instanceof classType) {
            temp = (classType) type;
            return name.equals(temp.getName());
        }
        return false;
    }
}
