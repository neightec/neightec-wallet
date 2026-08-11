import { Guest } from "../models/guest.model";
import { User } from "../models/user.model";

export const mockGuests: Guest[] = [];
export const mockDashboardPanels: any[] = [
  {
    id: 1,
    title: 'Einnahmen',
    revenue: '45678,00',
    currency: '€',
    change: '+20% month over month'
  },
  {
    id: 2,
    title: 'Ausgaben',
    revenue: '45678,00',
    currency: '€',
    change: '-20% month over month'
  },
  {
    id: 3,
    title: 'Ersparnisse',
    revenue: '45678,00',
    currency: '€',
    change: '+20% month over month'
  }
];

export const mockDashboardAccountPanels: any[] = [
  {
    id: 1,
    title: 'Deutsch Bank',
    revenue: '45678,00',
    currency: '€',
    change: '+20% month over month'
  },
  {
    id: 2,
    title: 'ING Bank',
    revenue: '45678,00',
    currency: '€',
    change: '-20% month over month'
  }
];

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