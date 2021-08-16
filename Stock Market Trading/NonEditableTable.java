import javax.swing.table.DefaultTableModel;

public class NonEditableTable extends DefaultTableModel{
            

    @Override //this is overriden since this prevents user changing fields
    public boolean isCellEditable(int row, int column){  
        return false;  
    }
    

}


