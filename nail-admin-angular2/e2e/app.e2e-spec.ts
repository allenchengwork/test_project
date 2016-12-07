import { NailAdminAngular2Page } from './app.po';

describe('nail-admin-angular2 App', function() {
  let page: NailAdminAngular2Page;

  beforeEach(() => {
    page = new NailAdminAngular2Page();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
