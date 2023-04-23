import { Adoption } from "./Adoption";
import { Customer } from "./Customer";

export interface AdoptionCustomer{
    id:	number;
    adoptionContract:String;
    customerFeedback:String;
    idAdoptionAdoptionCustomer:number;
    idCustomerAdoptionCustomer:number;
    adoptionAdoptionCustomer?:Adoption;
    customerAdoptionCustomer?:Customer;
}