package etf.ip.model;

import java.util.List;

public class CategoryDTO {
    private long id;
    private String name;
    private List<SpecificAttributeDTO> specificAttributes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpecificAttributeDTO> getSpecificAttributes() {
        return specificAttributes;
    }

    public void setSpecificAttributes(List<SpecificAttributeDTO> specificAttributes) {
        this.specificAttributes = specificAttributes;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specificAttributes=" + specificAttributes +
                '}';
    }
}
