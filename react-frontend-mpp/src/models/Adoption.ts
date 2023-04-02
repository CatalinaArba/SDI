import { Pet } from "./Pets";

export interface Adoption{
    id:	number;
    adoptionDate:string;
    adoptionFee:number;
    minimum: 1;
    adoptionStatus: string;
    adoptionLocation:string;
    adoptionNotes: string;
    pets:Pet[];
}