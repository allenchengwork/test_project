import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { AppModule } from './app.module';
import { AppSettings } from './app.settings';

if (AppSettings.APP_MODE != 'dev') {
    enableProdMode();
}

const platform = platformBrowserDynamic();
platform.bootstrapModule(AppModule);
