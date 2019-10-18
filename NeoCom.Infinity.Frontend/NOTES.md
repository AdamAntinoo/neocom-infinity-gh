[FRONTEND] Create acceptance support functions to search for panels and other elements during the testing. Use the promise pattern
or the direct access pattern so all code can use the same set of asserts.

[ANGULAR SNIPPETS]
return Observable.create((observer) => {
		let service = new Medico(JSON.parse(serviceData));
		observer.next(service);
		observer.complete();
});
