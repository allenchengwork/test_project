import { Interceptor, InterceptedRequest, InterceptedResponse } from 'ng2-interceptors';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';

export class ServerURLInterceptor implements Interceptor {
    public interceptBefore(request: InterceptedRequest): Observable<InterceptedRequest> {
        // Do whatever with request: get info or edit it
        console.log('interceptBefore');
        return Observable.of(request);
        /*
          You can return:
            - Request: The modified request
            - Nothing: For convenience: It's just like returning the request
            - <any>(Observable.throw("cancelled")): Cancels the request, interrupting it from the pipeline, and calling back 'interceptAfter' in backwards order of those interceptors that got called up to this point.
        */
    }

    public interceptAfter(response: InterceptedResponse): Observable<InterceptedResponse> {
        // Do whatever with response: get info or edit it
        console.log('interceptAfter');
		
		/*
        if (true) {
            return Observable.throw('OOO XXX');
        }
        */

        return Observable.of(response);
        /*
          You can return:
            - Response: The modified response
            - Nothing: For convenience: It's just like returning the response
        */
    }
}