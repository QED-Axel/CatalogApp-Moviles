import { PrismaService } from '../prisma/prisma.service';
import { CreateMediaItemDto } from './dto/create-media-item.dto';
import { UpdateMediaItemDto } from './dto/update-media-item.dto';
export declare class CatalogService {
    private prisma;
    constructor(prisma: PrismaService);
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
        count: number;
    }>;
    searchMovies(query: string): Promise<any>;
    getTrending(): Promise<any>;
}
