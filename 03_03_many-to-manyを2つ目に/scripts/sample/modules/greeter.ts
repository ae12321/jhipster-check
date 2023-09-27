export class Greeter {
  private name: string;

  constructor(name: string) {
    this.name = name;
  }

  sayHello() {
    console.log(`Hello, ${this.name}`);
  }
  sayHelloAfterSec(sec = 2) {
    return new Promise((resolve, _) => {
      setTimeout(() => {
        resolve(this.sayHello());
      }, sec * 1000);
    });
  }
}
