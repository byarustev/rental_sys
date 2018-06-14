/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralClasses;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author robert
 */
public interface RentalContract {
    public Double computeBalance(); //-ve balance implies an extra amount
    public ArrayList<Payment> getPayments(Date startDate, Date endDate);
    public ArrayList<Payment> getPayments();//returns payments from the start of the contract
    public String saveContract();
    public Tenant getAssociatedTenant();
}
