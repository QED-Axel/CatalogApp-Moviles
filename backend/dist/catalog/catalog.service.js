"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.CatalogService = void 0;
const common_1 = require("@nestjs/common");
const prisma_service_1 = require("../prisma/prisma.service");
let CatalogService = class CatalogService {
    prisma;
    constructor(prisma) {
        this.prisma = prisma;
    }
    async create(createMediaItemDto) {
        return this.prisma.mediaItem.create({
            data: createMediaItemDto,
        });
    }
    async findAll() {
        return this.prisma.mediaItem.findMany({
            orderBy: { createdAt: 'desc' },
        });
    }
    async findOne(id) {
        const item = await this.prisma.mediaItem.findUnique({
            where: { id },
        });
        if (!item) {
            throw new common_1.NotFoundException(`MediaItem with ID ${id} not found`);
        }
        return item;
    }
    async update(id, updateMediaItemDto) {
        try {
            return await this.prisma.mediaItem.update({
                where: { id },
                data: updateMediaItemDto,
            });
        }
        catch (error) {
            throw new common_1.NotFoundException(`MediaItem with ID ${id} not found`);
        }
    }
    async remove(id) {
        try {
            return await this.prisma.mediaItem.delete({
                where: { id },
            });
        }
        catch (error) {
            throw new common_1.NotFoundException(`MediaItem with ID ${id} not found`);
        }
    }
    async syncTmdb() {
        const apiKey = process.env.TMDB_API_KEY;
        if (!apiKey)
            throw new common_1.NotFoundException('Falta TMDB_API_KEY en el entorno');
        const response = await fetch(`https://api.themoviedb.org/3/movie/popular?api_key=${apiKey}&language=es-MX&page=1`);
        const data = await response.json();
        if (!data.results)
            return { count: 0 };
        let count = 0;
        for (const item of data.results) {
            const yearStr = item.release_date ? item.release_date.split('-')[0] : '2000';
            const yearNum = parseInt(yearStr, 10);
            await this.prisma.mediaItem.create({
                data: {
                    title: item.title,
                    type: 'movie',
                    synopsis: item.overview || 'Sin descripción',
                    imageUrl: item.poster_path ? `https://image.tmdb.org/t/p/w500${item.poster_path}` : null,
                    year: isNaN(yearNum) ? 2000 : yearNum,
                    genre: 'Popular'
                }
            });
            count++;
        }
        return { count };
    }
};
exports.CatalogService = CatalogService;
exports.CatalogService = CatalogService = __decorate([
    (0, common_1.Injectable)(),
    __metadata("design:paramtypes", [prisma_service_1.PrismaService])
], CatalogService);
//# sourceMappingURL=catalog.service.js.map