import { Component, Input, OnInit } from "@angular/core";

@Component({
  selector: "app-card-skeleton",
  templateUrl: "./card-skeleton.component.html",
  styleUrls: ["./../each-announcement/each-announcement.component.css"],
})
export class CardSkeletonComponent implements OnInit {
  @Input() isLoading = false;
  skeleton = new Array(5);
  constructor() {}

  ngOnInit() {}
}
