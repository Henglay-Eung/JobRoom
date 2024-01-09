import { Subject } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ShareDataService {
  itemPerPageSoure=new Subject<number>();
  itemPerPage=this.itemPerPageSoure.asObservable()


  totalItemSource=new Subject<number>();
  totalItem=this.totalItemSource.asObservable();

  currentPageSource=new Subject<number>();
  currentPage=this.currentPageSource.asObservable();

  
  isLoadingSource = new Subject<boolean>();
  isLoading=this.isLoadingSource.asObservable()

  dataSource=new Subject<any>();
  data=this.dataSource.asObservable();

  
  searchValueSource=new Subject<string>();
  searchValue=this.searchValueSource.asObservable()

  isSearchSource=new Subject<boolean>()
  isSearch=this.isSearchSource.asObservable()

  isFilterByDateSource=new Subject<boolean>();
  isFilterByDate=this.isFilterByDateSource.asObservable()
  
  totalPageSource=new Subject<number>()
  totalPage=this.totalPageSource.asObservable();

  constructor() { }
}
