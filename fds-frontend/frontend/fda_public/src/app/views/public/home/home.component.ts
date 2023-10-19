import { Component } from '@angular/core';
interface FoodItem {
  name: string;
  path: string;
}
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  foodList: FoodItem[] = [
    {
      name: 'thali',
      path: 'thali.png'
    },
    {
      name: 'pizza',
      path: 'pizza.png'
    },
    {
      name: 'burger',
      path: 'burger.png'
    },
    {
      name: 'cake',
      path: 'cake.png'
    },
    {
      name: 'paneer',
      path: 'paneer.png'
    },
    {
      name: 'north indian',
      path: 'north_indian_thali.png'
    },
    {
      name: '1 cake',
      path: 'cake.png'
    },
    {
      name: '1 thali',
      path: 'thali.png'
    },
    {
      name: '1 burger',
      path: 'burger.png'
    },
  ]
  cardItemList = [
    {
      name: 'Shodhani Sweets',
      image: "assets/images/sweets.jpg",
      offers: "45% off",
      rating: '4.3',
      items: 'Mithai, Street Food',
      pricing: 'Mithai, Street Food',
      timing: '34 min',
    },
    {
      name: 'Shodhani Sweets 1',
      image: "assets/images/sweets.jpg",
      offers: "45% off",
      rating: '4.3',
      items: 'Mithai, Street Food',
      pricing: 'Mithai, Street Food',
      timing: '34 min',
    },
    {
      name: 'Shodhani Sweets 2',
      image: "assets/images/sweets.jpg",
      offers: "45% off",
      rating: '4.3',
      items: 'Mithai, Street Food',
      pricing: 'Mithai, Street Food',
      timing: '34 min',
    },
    {
      name: 'Shodhani Sweets 5',
      image: "assets/images/sweets.jpg",
      offers: "45% off",
      rating: '4.3',
      items: 'Mithai, Street Food',
      pricing: 'Mithai, Street Food',
      timing: '34 min',
    },
    {
      name: 'Shodhani Sweets 6',
      image: "assets/images/sweets.jpg",
      offers: "45% off",
      rating: '4.3',
      items: 'Mithai, Street Food',
      pricing: 'Mithai, Street Food',
      timing: '34 min',
    },
    {
      name: 'Shodhani Sweets 7',
      image: "assets/images/sweets.jpg",
      offers: "45% off",
      rating: '4.3',
      items: 'Mithai, Street Food',
      pricing: 'Mithai, Street Food',
      timing: '34 min',
    },
  ]

  slidePosition = '0';

  nextSlide() {
    this.slidePosition = '1';
    this.foodList?.push(this.foodList.shift() as FoodItem);
    this.slidePosition = '0';
  }

  previousSlide() {
    this.slidePosition = '-1';
    this.foodList?.unshift(this.foodList.pop() as FoodItem);
    this.slidePosition = '0';
  }
}
