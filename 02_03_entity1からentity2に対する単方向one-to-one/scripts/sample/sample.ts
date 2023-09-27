import { Greeter } from './modules/greeter';

const items = [1, 2, 3, 4, 5];
const sum = items.reduce((acc, value) => acc + value, 0);
console.log(sum);

const greeter = new Greeter('aki');

// console.log('start');
// greeter.sayHelloAfterSec();
// greeter.sayHello();
// console.log('end');

(async function () {
  console.log('start');
  await greeter.sayHelloAfterSec();
  greeter.sayHello();
  console.log('end');
})();
