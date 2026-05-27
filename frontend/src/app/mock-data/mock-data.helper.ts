import { Guest } from "../models/guest.model";
import { User } from "../models/user.model";

export const mockGuests: Guest[] = [];
const numGuests = 30; // Number of guests to generate

for (let i = 0; i < numGuests; i++) {
  const guest: Guest = {
    id: i + 1, // needs to be handled in backend
    name: 'Max Mustermann ' + (i + 1) ,
    contact: 'max.mustermann@mail.com',
    status: 'Come',
    address: 'Max Mustermann Address ' + (i + 1)
  };
  mockGuests.push(guest);
}

export const mockUser: User = {
  id: 1,
  name: 'Max Mustermann',
  email: 'max.mustermann@mail.com',
  phone: '+41123456789',
};