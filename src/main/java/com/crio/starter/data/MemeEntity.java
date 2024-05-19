package com.crio.starter.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "xmeme")
@NoArgsConstructor
public class MemeEntity {
    
    @Id
    private String id;
    private String name;
    private String url;
    private String caption;

    public boolean hasEmptyFields() {
        return name == null || name.trim().isEmpty() || 
               url == null || url.trim().isEmpty() || 
               caption == null || caption.trim().isEmpty();
    }
}
