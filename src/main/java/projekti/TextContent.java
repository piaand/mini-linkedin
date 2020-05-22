/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author piaandersin
 */

@Data @AllArgsConstructor @NoArgsConstructor
public class TextContent {
    
    @NotNull
    @Size(min=1, max=250)
    private String content;
}
