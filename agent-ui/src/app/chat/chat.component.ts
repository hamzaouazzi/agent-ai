import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClient, HttpDownloadProgressEvent, HttpEventType} from '@angular/common/http';
import {MarkdownComponent} from 'ngx-markdown';

@Component({
  selector: 'app-chat',
  imports: [
    FormsModule,
    MarkdownComponent
  ],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent {

  query : String = "";
  response : any;
  progress : boolean = false;

  constructor(private http: HttpClient) {
  }

  askAgent() {
    this.response = "";
    this.progress = true;
    this.http.get('http://localhost:8080/askAgent?query=' + this.query,
      {
        responseType: 'text', observe: "events", reportProgress: true
      })
      .subscribe({
        next: (evt) => {
          if (evt.type === HttpEventType.DownloadProgress) {
            this.response = (evt as HttpDownloadProgressEvent).partialText;
          }
        },
        error: (err) => {
          console.log(err);
        },
        complete: () => {
          console.log('done');
          this.progress = false;
        }
      })

  }
}
