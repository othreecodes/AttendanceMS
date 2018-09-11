package mainform;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

public class DialogController implements Initializable{

	private JFXButton acceptButton;
       
	private JFXDialog dialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
 
 System.out.println("In the init");
		acceptButton.setOnMouseClicked((e)->{
			dialog.close();
		});
    
                
    }
    
    void showDialog(StackPane root){
     System.out.println("show jhoor");
     dialog.setTransitionType(DialogTransition.CENTER);
     
    dialog.show(root);
    }

	/**@PostConstruct
	public void init() throws FlowException, VetoException {

		centerButton.setOnMouseClicked((e)->{
			dialog.setTransitionType(DialogTransition.CENTER);
			dialog.show((StackPane) context.getRegisteredObject("ContentPane"));
		});

		topButton.setOnMouseClicked((e)->{
			dialog.setTransitionType(DialogTransition.TOP);
			dialog.show((StackPane) context.getRegisteredObject("ContentPane"));
		});

		rightButton.setOnMouseClicked((e)->{
			dialog.setTransitionType(DialogTransition.RIGHT);
			dialog.show((StackPane) context.getRegisteredObject("ContentPane"));
		});

		bottomButton.setOnMouseClicked((e)->{
			dialog.setTransitionType(DialogTransition.BOTTOM);
			dialog.show((StackPane) context.getRegisteredObject("ContentPane"));
		});

		leftButton.setOnMouseClicked((e)->{
			dialog.setTransitionType(DialogTransition.LEFT);
			dialog.show((StackPane) context.getRegisteredObject("ContentPane"));
		});

		acceptButton.setOnMouseClicked((e)->{
			dialog.close();
		});
	
        * }

* */
        
        
}
