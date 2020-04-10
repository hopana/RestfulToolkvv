package cn.vv.plugin.restfultookvv.restful.annotations;


public enum JaxrsPathAnnotation implements PathMappingAnnotation {

    PATH("Path", "javax.ws.rs.Path");

    JaxrsPathAnnotation(String shortName, String qualifiedName) {
        this.shortName = shortName;
        this.qualifiedName = qualifiedName;
    }

    private String shortName;
    private String qualifiedName;

    public String getQualifiedName() {
        return qualifiedName;
    }

    public String getShortName() {
        return shortName;
    }
/*
    @Override
    public List<PathMappingAnnotation> getPathMappings() {
        return allPathMappingAnnotations;
    }

    static {
        for (JaxrsPathAnnotation annotation : JaxrsPathAnnotation.values()) {
            allPathMappingAnnotations.add(annotation);
        }
    }*/

}