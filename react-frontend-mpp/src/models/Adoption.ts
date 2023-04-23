import { AdoptionCustomer } from "./AdoptionCustomer";
import { Pet } from "./Pets";

export interface Adoption{
    id:	number;
    adoptionDate:string;
    adoptionFee:number;
    adoptionStatus: string;
    adoptionLocation:string;
    adoptionNotes: string;
    pet:Pet[];
    adoptionCustomers:AdoptionCustomer[];
}