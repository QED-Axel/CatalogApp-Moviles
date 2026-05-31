"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.UpdateMediaItemDto = void 0;
const mapped_types_1 = require("@nestjs/mapped-types");
const create_media_item_dto_1 = require("./create-media-item.dto");
class UpdateMediaItemDto extends (0, mapped_types_1.PartialType)(create_media_item_dto_1.CreateMediaItemDto) {
}
exports.UpdateMediaItemDto = UpdateMediaItemDto;
//# sourceMappingURL=update-media-item.dto.js.map