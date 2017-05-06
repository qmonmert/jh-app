import { Author } from '../author';
export class Book {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public publicationDate?: any,
        public price?: number,
        public author?: Author,
    ) {
    }
}
