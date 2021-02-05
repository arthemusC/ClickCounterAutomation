/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prodcount;

/**
 *
 * @author LANARTE
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class fileSaver {
    
    
    
    public fileSaver(){
        try {
           File LogProd = new File("LOG DE TRABALHO.txt"); 
           if(LogProd.createNewFile()){
               System.out.println("novo arquivo criado: " + LogProd.getName());
            
           } else {
               System.out.println("o arquivo já existe");
           }
               
        }
        
        catch (IOException e){
            System.out.println("Ocorreu um erro");
            e.printStackTrace();
        }
             
    }
    

}


