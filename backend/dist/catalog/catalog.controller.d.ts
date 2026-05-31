import { CatalogService } from './catalog.service';
import { CreateMediaItemDto } from './dto/create-media-item.dto';
import { UpdateMediaItemDto } from './dto/update-media-item.dto';
export declare class CatalogController {
    private readonly catalogService;
    constructor(catalogService: CatalogService);
    create(createMediaItemDto: CreateMediaItemDto): Promise<{
        title: string;
        type: string;
        synopsis: string;
        imageUrl: string | null;
        year: number;
        genre: string;
        createdAt: Date;
        id: number;
    }>;
    findAll(): Promise<{
        title: string;
        type: string;
        synopsis: string;
        imageUrl: string | null;
        year: number;
        genre: string;
        createdAt: Date;
        id: number;
    }[]>;
    findOne(id: number): Promise<{
        title: string;
        type: string;
        synopsis: string;
        imageUrl: string | null;
        year: number;
        genre: string;
        createdAt: Date;
        id: number;
    }>;
    update(id: number, updateMediaItemDto: UpdateMediaItemDto): Promise<{
        title: string;
        type: string;
        synopsis: string;
        imageUrl: string | null;
        year: number;
        genre: string;
        createdAt: Date;
        id: number;
    }>;
    remove(id: number): Promise<{
        title: string;
        type: string;
        synopsis: string;
        imageUrl: string | null;
        year: number;
        genre: string;
        createdAt: Date;
        id: number;
    }>;
    syncTmdb(): Promise<{
        message: string;
        result: {
            count: number;
        };
    }>;
}
