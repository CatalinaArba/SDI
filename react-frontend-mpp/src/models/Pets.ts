import { Adoption } from "./Adoption";

export interface Pet{
    id:number;
    name:string;
    petType:string;
    age:number;
    gender:string
    price:number;
    description:string;
    adoption?:Adoption;
    
}