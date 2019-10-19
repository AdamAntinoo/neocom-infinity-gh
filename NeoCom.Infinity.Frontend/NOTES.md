[GIT]
-- CHECKOUT A FILE FROM A COMMIT
git checkout 315e21e99c0121e5679f58b386799d2d800531a1 NeoCom.Infinity.Frontend/e2e/src/features/NIF02-DashboardHome.feature


[FRONTEND] Create acceptance support functions to search for panels and other elements during the testing. Use the promise pattern
or the direct access pattern so all code can use the same set of asserts.

[ANGULAR SNIPPETS]
return Observable.create((observer) => {
		let service = new Medico(JSON.parse(serviceData));
		observer.next(service);
		observer.complete();
});

[FILTER]
    this._filters.push(new Filter()
      .setName('has contents')
      .setDescription('Filter Especialidades empty of Medico')
      .setFilter((_test: Especialidad): boolean => {
        if (_test.getContentSize() < 1) return true;
        else return false;
      }));

    this.filters.push({
      name: 'appointment < now',
      description: 'Filter appointment in the past',
      filter: (_target: Cita): boolean => {
        return isBefore(_target.getFecha(), new Date());
      }
    });

      let filterResult = filter.filterFunction(_target);
