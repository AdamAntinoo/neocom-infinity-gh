//  PROJECT:     A5POC (A5POC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 5
//  DESCRIPTION: Proof of concept projects.
//--- CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
//--- SERVICES
// import { AppModelStoreService } from '../../../services/app-model-store.service';
//--- INTERFACES
//--- MODELS
import { Job } from '../../../../models/Job.model';
import { JobsComponent } from '../../../../components/dragdrop/jobs/jobs.component';

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.scss']
})
export class JobComponent implements OnInit {
  @Input() viewer: JobsComponent;
  @Input() node: Job;

  constructor() { }

  ngOnInit() {
  }

}
