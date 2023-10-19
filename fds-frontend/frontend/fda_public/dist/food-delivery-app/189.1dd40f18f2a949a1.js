"use strict";(self.webpackChunkfood_delivery_app=self.webpackChunkfood_delivery_app||[]).push([[189],{6189:(P,u,e)=>{e.r(u),e.d(u,{ContactModule:()=>b});var s=e(6814),f=e(3403),t=e(5879),n=e(6223);let h=(()=>{var o;class r{constructor(a){this._formBuilder=a}createForm(){return this._formBuilder.group({query:["",n.kI.required],fullName:["",n.kI.required],email:["",[n.kI.required,n.kI.email]],mobileNumber:["",[n.kI.required,n.kI.pattern("[0-9]*")]],text:["",n.kI.required]})}}return(o=r).\u0275fac=function(a){return new(a||o)(t.LFG(n.qu))},o.\u0275prov=t.Yz7({token:o,factory:o.\u0275fac,providedIn:"root"}),r})();var i=e(5195),l=e(9157),p=e(2032),g=e(2296),C=e(8525),v=e(3680),d=e(3814);function x(o,r){if(1&o&&(t.TgZ(0,"mat-option",15),t._uU(1),t.qZA()),2&o){const c=r.$implicit;t.Q6J("value",c),t.xp6(1),t.hij(" ",c," ")}}const M=[{path:"",component:(()=>{var o;class r{constructor(a){this._contactUsService=a,this.queryList=["I need help with my Zomato online order.","I found incorrect/outdated information on a page.","There is a photo/review that is bothering me and I would like to report it.","The website/app are not working the way they should.","I would like to give feedback/suggestions.","I need some help with my blog."],this.ContactusForm=this._contactUsService.createForm()}onSubmit(){console.log(this.ContactusForm.value),this.ContactusForm.reset()}}return(o=r).\u0275fac=function(a){return new(a||o)(t.Y36(h))},o.\u0275cmp=t.Xpm({type:o,selectors:[["app-contact"]],decls:46,vars:3,consts:[["fxLayout","row","fxLayoutAlign","center center",1,"jumbotron"],["fxLayout","row",1,"container"],["fxFlex","60%"],["fxLayout","column",3,"formGroup","ngSubmit"],["formControlName","query","placeholder","How can we help you?"],[3,"value",4,"ngFor","ngForOf"],["formControlName","fullName","matInput","","placeholder","Full Name"],["formControlName","email","matInput","","placeholder","Email Address"],["formControlName","mobileNumber","matInput","","placeholder","Mobile Number (optional)","pattern","[0-9]*"],[1,"TextFieldControl"],["formControlName","text","matInput","","placeholder","Type text"],["mat-raised-button","","type","submit",1,"FeedbackBtn",3,"disabled"],["fxFlex","40%","fxLayout","column",1,"cardSection"],[1,"example-card"],["mat-button",""],[3,"value"]],template:function(a,m){1&a&&(t.TgZ(0,"div")(1,"div",0)(2,"h1"),t._uU(3,"We would love to hear from you!"),t.qZA()(),t.TgZ(4,"div",1)(5,"div",2)(6,"form",3),t.NdJ("ngSubmit",function(){return m.onSubmit()}),t.TgZ(7,"mat-form-field")(8,"mat-select",4),t.YNc(9,x,2,2,"mat-option",5),t.qZA()(),t.TgZ(10,"mat-form-field")(11,"mat-label"),t._uU(12,"Full Name"),t.qZA(),t._UZ(13,"input",6),t.qZA(),t.TgZ(14,"mat-form-field")(15,"mat-label"),t._uU(16,"Email Address"),t.qZA(),t._UZ(17,"input",7),t.qZA(),t.TgZ(18,"mat-form-field")(19,"mat-label"),t._uU(20,"Mobile Number"),t.qZA(),t._UZ(21,"input",8),t.qZA(),t.TgZ(22,"mat-form-field",9)(23,"mat-label"),t._uU(24,"Type Text"),t.qZA(),t._UZ(25,"textarea",10),t.qZA(),t.TgZ(26,"button",11),t._uU(27,"Submit Feedback"),t.qZA()()(),t.TgZ(28,"div",12)(29,"mat-card",13)(30,"mat-card-header")(31,"mat-card-title"),t._uU(32,"Report a Safety Emergency"),t.qZA()(),t.TgZ(33,"mat-card-content")(34,"p"),t._uU(35,"We are committed to the safety of everyone using Zomato."),t.qZA()(),t.TgZ(36,"mat-card-actions")(37,"button",14),t._uU(38,"Report here"),t.qZA()()(),t.TgZ(39,"mat-card",13)(40,"mat-card-header")(41,"mat-card-title"),t._uU(42,"Issue with your live order?"),t.qZA()(),t.TgZ(43,"mat-card-content")(44,"p"),t._uU(45,"Click on the 'Support' or 'Online ordering help' section in your app to connect to our customer support team."),t.qZA()()()()()()),2&a&&(t.xp6(6),t.Q6J("formGroup",m.ContactusForm),t.xp6(3),t.Q6J("ngForOf",m.queryList),t.xp6(17),t.Q6J("disabled",m.ContactusForm.invalid))},dependencies:[s.sg,i.a8,i.hq,i.dn,i.dk,i.n5,n._Y,n.Fj,n.JJ,n.JL,n.c5,n.sg,n.u,l.KE,l.hX,p.Nt,g.lW,C.gD,v.ey,d.xw,d.Wh,d.yH],styles:[".jumbotron[_ngcontent-%COMP%]{background:url(/assets/images/contactUs.avif) no-repeat;width:100%;height:22vw}.jumbotron[_ngcontent-%COMP%]   h1[_ngcontent-%COMP%]{font-size:3.5rem;font-weight:400;color:#fff}.container[_ngcontent-%COMP%]{padding:20px 120px;gap:60px}.container[_ngcontent-%COMP%]   form[_ngcontent-%COMP%]{gap:25px}.container[_ngcontent-%COMP%]   form[_ngcontent-%COMP%]   .mat-mdc-form-field[_ngcontent-%COMP%]{border:2px solid ghostwhite;border-radius:10px;overflow:hidden}.container[_ngcontent-%COMP%]   form[_ngcontent-%COMP%]   .FeedbackBtn[_ngcontent-%COMP%]{width:max-content;border-radius:10px;color:#fff;font-weight:200;background-color:#ef4f5f}.container[_ngcontent-%COMP%]   form[_ngcontent-%COMP%]   textarea#mat-input-5[_ngcontent-%COMP%]{height:120px}.container[_ngcontent-%COMP%]   .cardSection[_ngcontent-%COMP%]{gap:25px}.container[_ngcontent-%COMP%]   .cardSection[_ngcontent-%COMP%]   .mat-mdc-card[_ngcontent-%COMP%]{font-family:Arial,Helvetica,sans-serif;border-radius:15px;box-shadow:4px 4px 4px #0003,-4px 4px 4px #0003;line-height:1.5}.container[_ngcontent-%COMP%]   .cardSection[_ngcontent-%COMP%]   .mat-mdc-card[_ngcontent-%COMP%]   .mat-mdc-card-header[_ngcontent-%COMP%]{font-size:24px;color:#1c1c1c;font-weight:500;padding-bottom:20px}.container[_ngcontent-%COMP%]   .cardSection[_ngcontent-%COMP%]   .mat-mdc-card[_ngcontent-%COMP%]   .mat-mdc-card-content[_ngcontent-%COMP%]{font-size:18px;color:#4f4f4f;font-weight:200}.container[_ngcontent-%COMP%]   .cardSection[_ngcontent-%COMP%]   .mat-mdc-card[_ngcontent-%COMP%]   .mat-mdc-button[_ngcontent-%COMP%]:not(:disabled){color:#ef4f5f!important}"]}),r})()}];let y=(()=>{var o;class r{}return(o=r).\u0275fac=function(a){return new(a||o)},o.\u0275mod=t.oAB({type:o}),o.\u0275inj=t.cJS({imports:[f.Bz.forChild(M),f.Bz]}),r})();var Z=e(617),O=e(1447);let b=(()=>{var o;class r{}return(o=r).\u0275fac=function(a){return new(a||o)},o.\u0275mod=t.oAB({type:o}),o.\u0275inj=t.cJS({imports:[s.ez,y,i.QW,n.UX,n.u5,l.lN,p.c,g.ot,Z.Ps,C.LD,O.o9]}),r})()}}]);