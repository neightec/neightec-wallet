# Neightec-Wallet
Open Source Money Management

## Project Structure

```text
.
├── frontend   # React / UI application
├── backend    # API / server application
└── docker     # Docker configuration and services
```

---

## Prerequisites

Make sure the following tools are installed:

- Node.js v20
- NVM (Node Version Manager)
- Yarn

### Fedora / Linux Build Tools

Some dependencies (e.g. `node-sass`) require native build tools.

Install them with:

```bash
sudo dnf install gcc-c++ make python3
```

---

## Run Frontend Locally

Navigate to the frontend directory:

```bash
cd frontend
```

Install and use Node.js v20:

```bash
nvm install 20
nvm use 20
```

Clean old dependencies:

```bash
rm -rf node_modules
rm -f yarn.lock
```

Install dependencies and start the development server:

```bash
yarn install
yarn start
```

---

## Notes

- Make sure your Node.js version matches the project requirements.
- If installation issues occur, try clearing the Yarn cache:

```bash
yarn cache clean
```

- Older dependencies such as `node-sass` may require additional system libraries or build tools.
