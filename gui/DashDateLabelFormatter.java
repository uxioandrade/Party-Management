/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.JFormattedTextField.AbstractFormatter;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.text.DateFormat;
/**
 *
 * @author alumnogreibd
 */
public class DashDateLabelFormatter extends AbstractFormatter{
    
    private final String  datePattern = "yyyy-dd-MM";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
    
    @Override
    public Object stringToValue(String text) throws ParseException{
        return dateFormatter.parseObject(text);
    }
    
    @Override
    public String valueToString(Object value) throws ParseException{
        if(value != null){
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        
        return "";
    }
}
