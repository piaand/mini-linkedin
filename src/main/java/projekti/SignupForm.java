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
public class SignupForm  {
    
    @NotNull
    @Size(min=2, max=50)
    private String name;
    
    @NotNull
    @Size(min=2, max=50)
    private String username;
    
    @NotNull
    @Size(min=2, max=50)
    private String password;
    
    @NotNull
    @Size(min=2, max=50)
    private String profile;
    
}
