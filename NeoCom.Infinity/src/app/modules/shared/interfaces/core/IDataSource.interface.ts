//  PROJECT:     CitaMed.lib(CITM.LIB)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.1 Library
//  DESCRIPTION: CitaMed. Componente libreria. Este projecto contiene gran parte del código Typescript que puede
//               ser reutilizado en otros aplicativos del mismo sistema (CitaMed) o inclusive en otros
//               desarrollos por ser parte de la plataforma MVC de despliegue de nodos extensibles y
//               interacciones con elementos seleccionables.
//--- MODELS
import { INode } from './INode.interface';

export interface IDataSource {
  removeNode(_node: INode): boolean;
  updateNode(_node: INode): boolean;
}
