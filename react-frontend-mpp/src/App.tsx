import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import React from 'react'
import { PetsShowAll } from './components/pets/PetsShowAll'
import { AppHome } from './components/AppHome'
import { AppMenu } from './components/AppManu'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { PetDetails } from './components/pets/PetDetails'
import { PetDelete } from './components/pets/PetDelete'
import { PetAdd } from './components/pets/PetAdd'
import { PetUpdate } from './components/pets/PetUpdate'
import { AdoptionShowAll } from './components/adoptions/AdoptionShowAll'
import { AdoptionCustomerShowAll } from './components/adoptionCustomers/AdoptionCustomerShowAll'
import { AdoptionDetails } from './components/adoptions/AdoptionDetails'
import { AdoptionCustomerDetails } from './components/adoptionCustomers/AdoptionCustomerDetails'
import { AdoptionAdd } from './components/adoptions/AdoptionAdd'
import { AdoptionCustomerAdd } from './components/adoptionCustomers/AdoptionCustomerAdd'
import { AdoptionDelete } from './components/adoptions/AdoptionDelete'
import { AdoptionCustomerDelete } from './components/adoptionCustomers/AdoptionCustomerDetele'
import { AdoptionCustomerUpdate } from './components/adoptionCustomers/AdoptionCustomerUpdate'
import { AdoptionUpdate } from './components/adoptions/AdoptionUpdate'
import { AdoptionOrderedByAvgPetPriceStatistics } from './components/adoptions/AdoptionOrderedByAvgPetPriceStatistics'
import { PetFilterPrice } from './components/pets/PetFilterPrice'


function App() {
  return (
		<React.Fragment>
			<Router>
				<AppMenu />

				<Routes>
					<Route path="/" element={<AppHome />} />
					<Route path="/pets" element={<PetsShowAll />} />
					<Route path="/pets/:id/details" element={<PetDetails />} />
					<Route path="/pets/:id/delete" element={<PetDelete />} />
					<Route path="/pets/:id/edit" element={<PetUpdate />} />
					<Route path="/pets/add" element={<PetAdd />} />
					<Route path="/pets/price" element={<PetFilterPrice />} />
					<Route path="/adoptions" element={<AdoptionShowAll />} />
					<Route path="/adoptionCustomer" element={<AdoptionCustomerShowAll />} />
					<Route path="/adoptions/:id/details" element={<AdoptionDetails />} />
					<Route path="/adoptionCustomer/:id/details" element={<AdoptionCustomerDetails />} />
					<Route path="/adoptions/add" element={<AdoptionAdd />} />
					<Route path="/adoptionCustomer/add" element={<AdoptionCustomerAdd />} />
					<Route path="/adoptions/:id/delete" element={<AdoptionDelete />} />
					<Route path="/adoptionCustomer/:id/delete" element={<AdoptionCustomerDelete />} />
					<Route path="/adoptionCustomer/:id/edit" element={<AdoptionCustomerUpdate />} />
					<Route path="/adoptions/:id/edit" element={<AdoptionUpdate />} />
					<Route path="/adoptions/avgPetPrice" element={<AdoptionOrderedByAvgPetPriceStatistics />} />

					
				</Routes>
			</Router>
		</React.Fragment>
	);
}

export default App
