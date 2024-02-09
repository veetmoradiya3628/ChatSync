import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-chat-thread',
  templateUrl: './chat-thread.component.html',
  styleUrls: ['./chat-thread.component.scss']
})
export class ChatThreadComponent implements OnInit {
  ngOnInit(): void {
  }

  messages: { text: string, sentBy: string }[] = [
    { text: 'Hello!', sentBy: 'user' },
    { text: 'Hello!', sentBy: 'user' },
    { text: 'Hello!', sentBy: 'user' },
    { text: 'Hi there!', sentBy: 'other' },
    { text: 'Hi there!', sentBy: 'other' },
    { text: 'How are you doing?', sentBy: 'user' },
    { text: 'Nice to meet you!', sentBy: 'other' },
    { text: 'This is a longer message...', sentBy: 'user' },
    { text: 'Short message', sentBy: 'other' },
    { text: 'Long messages can contain more content and vary in length.', sentBy: 'user' },
    { text: 'Hi!', sentBy: 'other' },
    { text: 'Hello, how are you?', sentBy: 'user' },
    { text: 'Another short one.', sentBy: 'other' },
    { text: 'Yet another message.', sentBy: 'user' },
    { text: 'Hello!', sentBy: 'user' },
    { text: 'Hello!', sentBy: 'user' },
    { text: 'Hello!', sentBy: 'user' },
    { text: 'Hi there!', sentBy: 'other' },
    { text: 'Hi there!', sentBy: 'other' },
    { text: 'How are you doing?', sentBy: 'user' },
    { text: 'Nice to meet you!', sentBy: 'other' },
    { text: 'This is a longer message...', sentBy: 'user' },
    { text: 'Short message', sentBy: 'other' },
    { text: 'Long messages can contain more content and vary in length.', sentBy: 'user' },
    { text: 'Hi!', sentBy: 'other' },
    { text: 'Hello, how are you?', sentBy: 'user' },
    { text: 'Another short one.', sentBy: 'other' },
    { text: 'Yet another message.', sentBy: 'user' },
    { text: 'Hello!', sentBy: 'user' },
    { text: 'Hello!', sentBy: 'user' },
    { text: 'Hello!', sentBy: 'user' },
    { text: 'Hi there!', sentBy: 'other' },
    { text: 'Hi there!', sentBy: 'other' },
    { text: 'How are you doing?', sentBy: 'user' },
    { text: 'Nice to meet you!', sentBy: 'other' },
    { text: 'This is a longer message...', sentBy: 'user' },
    { text: 'Short message', sentBy: 'other' },
    { text: 'Long messages can contain more content and vary in length.', sentBy: 'user' },
    { text: 'Hi!', sentBy: 'other' },
    { text: 'Hello, how are you?', sentBy: 'user' },
    { text: 'Another short one.', sentBy: 'other' },
    { text: 'Yet another message.', sentBy: 'user' },
    { text: 'Hello!', sentBy: 'user' },
    { text: 'Hello!', sentBy: 'user' },
    { text: 'Hello!', sentBy: 'user' },
    { text: 'Hi there!', sentBy: 'other' },
    { text: 'Hi there!', sentBy: 'other' },
    { text: 'How are you doing?', sentBy: 'user' },
    { text: 'Nice to meet you!', sentBy: 'other' },
    { text: 'This is a longer message...', sentBy: 'user' },
    { text: 'Short message', sentBy: 'other' },
    { text: 'Long messages can contain more content and vary in length.', sentBy: 'user' },
    { text: 'Hi!', sentBy: 'other' },
    { text: 'Hello, how are you?', sentBy: 'user' },
    { text: 'Another short one.', sentBy: 'other' },
    { text: 'Yet another message.', sentBy: 'user' },
];
  newMessage: string = '';

  sendMessage() {
    if (this.newMessage.trim() !== '') {
      this.messages.push({ text: this.newMessage, sentBy: 'user' });
      this.newMessage = '';
      // Logic to send message to other user or backend can be added here
    }
  }

}
