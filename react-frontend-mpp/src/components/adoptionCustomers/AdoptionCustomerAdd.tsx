import {
  Autocomplete,
  Button,
  Card,
  CardActions,
  CardContent,
  IconButton,
  TextField,
} from "@mui/material";
import { Container } from "@mui/system";
import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { Pet } from "../../models/Pets";
import { GlobalURL } from "../../main";
import { Adoption } from "../../models/Adoption";
import { debounce } from "lodash";
import { AdoptionCustomer } from "../../models/AdoptionCustomer";
import { Customer } from "../../models/Customer";
import { BACKEND_API_URL } from "../../constants";

export const AdoptionCustomerAdd = () => {
  const navigate = useNavigate();

  const [adoptionCustomer, setAdoptionsCustomer] = useState<AdoptionCustomer>({
    id: 0,
    adoptionContract: "",
    customerFeedback: "",
    idAdoptionAdoptionCustomer: 0,
    idCustomerAdoptionCustomer: 0,
  });

  const addPet = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      //await axios.post(`/api/pets/add`, pet);
      await axios.post(GlobalURL + `/adoptionCustomer`, adoptionCustomer);
      navigate("/adoptionCustomer");
    } catch (error) {
      console.log(error);
    }
  };

  const [adoptions, setAdoptions] = useState<Adoption[]>([]);
  const [customers, setCustomers] = useState<Customer[]>([]);

  const fetchCustomersSuggestions = async (query: string) => {
    try {
      let url = GlobalURL + `/customers/autocomplete?query=${query}`;

      const response = await fetch(url);

      const data = await response.json();

      setCustomers(data);

      console.log(data);
    } catch (error) {
      console.log("Error fetching suggestions:", error);
    }
  };
  const fetchAdoptionSuggestions = async (query: string) => {
    try {
      //let url = GlobalURL + `/adoptions/autocomplete?query=${query}`;
      let url =  `${BACKEND_API_URL}/adoptions/autocomplete?query=${query}`;

      const response = await fetch(url);

      const data = await response.json();

      setAdoptions(data);

      console.log(data);
    } catch (error) {
      console.log("Error fetching suggestions:", error);
    }
  };

  const debouncedFetchCustomersSuggestions = useCallback(
    debounce(fetchCustomersSuggestions, 500),
    []
  );
  const debouncedFetchAdoptionsSuggestions = useCallback(
    debounce(fetchAdoptionSuggestions, 500),
    []
  );

  const handleAdoptionsInputChange = (event: any, value: any, reason: any) => {
    console.log("input", value, reason);

    if (reason == "input") {
      debouncedFetchAdoptionsSuggestions(value);
    }
  };
  const handleCustomersInputChange = (event: any, value: any, reason: any) => {
    console.log("input", value, reason);

    if (reason == "input") {
      debouncedFetchCustomersSuggestions(value);
    }
  };

  useEffect(() => {
    return () => {
      debouncedFetchAdoptionsSuggestions.cancel();
      debouncedFetchCustomersSuggestions.cancel();
    };
  }, [debouncedFetchAdoptionsSuggestions, debouncedFetchCustomersSuggestions]);

  return (
    <Container>
      <Card>
        <CardContent>
          <IconButton component={Link} sx={{ mr: 3 }} to={`/pets`}>
            <ArrowBackIcon />
          </IconButton>{" "}
          <form onSubmit={addPet}>
            <TextField
              id="contract"
              label="Contract"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setAdoptionsCustomer({
                  ...adoptionCustomer,
                  adoptionContract: event.target.value,
                })
              }
            />
            <TextField
              id="feedback"
              label="Feedback"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setAdoptionsCustomer({
                  ...adoptionCustomer,
                  customerFeedback: event.target.value,
                })
              }
            />
            <Autocomplete
              id="adoption"
              options={adoptions}
              getOptionLabel={(option) =>
                `${option.id},  ${option.adoptionDate}, ${option.adoptionLocation}, ${option.adoptionStatus}, ${option.adoptionNotes}`
              }
              renderInput={(params) => (
                <TextField {...params} label="Adoption ID" variant="outlined" />
              )}
              filterOptions={(options, date) =>
                options.filter((option) =>
                  option.id.toString().startsWith(date.inputValue)
                )
              }
              onInputChange={handleAdoptionsInputChange}
              onChange={(event, value) => {
                if (value) {
                  setAdoptionsCustomer({
                    ...adoptionCustomer,
                    idAdoptionAdoptionCustomer: value.id,
                  });
                }
              }}
            />
            <div>
              <br></br>
            </div>
            <Autocomplete
              id="customer"
              options={customers}
              getOptionLabel={(option) =>
                `${option.firstName}  ${option.lastName}`
              }
              renderInput={(params) => (
                <TextField
                  {...params}
                  label="Customer first name"
                  variant="outlined"
                />
              )}
              filterOptions={(options, state) =>
                options.filter((option) =>
                  option.firstName
                    .toLocaleLowerCase()
                    .includes(state.inputValue.toLocaleLowerCase())
                )
              }
              onInputChange={handleCustomersInputChange}
              onChange={(event, value) => {
                if (value) {
                  setAdoptionsCustomer({
                    ...adoptionCustomer,
                    idCustomerAdoptionCustomer: value.id,
                  });
                }
              }}
            />

            <Button type="submit">Add adoption-customer</Button>
          </form>
        </CardContent>
      </Card>
    </Container>
  );
};
