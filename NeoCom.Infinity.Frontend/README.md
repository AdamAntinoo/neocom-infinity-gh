# NeoComInfinity

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 8.2.3.

## Development distribution server full stack

Run the build command to compile the application to the distribution server directory 'npm run build:dev'
Redirect the default 80 port to the server listening port 'sudo iptables -t nat -I OUTPUT -p tcp -d 127.0.0.1 --dport 80 -j REDIRECT --to-ports 8080'
Start the backend simulation with the command 'npm run start:support'
Run the Procfile command 'node server.js'

Navigate to `http://http://neocom.infinity.local`.
The app will not reload because it is using the compilation ditributed code.

## Development frontend server

Redirect the default 4200 port to the server listening port 'sudo iptables -t nat -I OUTPUT -p tcp -d 127.0.0.1 --dport 80 -j REDIRECT --to-ports 4200'
Run the command 'npm run start:dev' to start the Angular development server. This server will not have the front initial page and should be started with a valid url code.
Navigate to 'http://neocom.infinity.local/' to start the login and show the dashboard.

## Build

Run `npm run build:dev` to build the project. The build artifacts will be stored in the `dist/` directory.
Use the `npm run build:prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `npm run e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).
Run `npm run e2e:atdd` to execute the end-to-end tests with Cucumber scenario definitions [Protractor](http://www.protractortest.org/).
